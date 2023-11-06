import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('CurrentMarketSegmentation e2e test', () => {
  const currentMarketSegmentationPageUrl = '/current-market-segmentation';
  const currentMarketSegmentationPageUrlPattern = new RegExp('/current-market-segmentation(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const currentMarketSegmentationSample = { value: 'purple Movies Wyoming', language: 'ENGLISH' };

  let currentMarketSegmentation;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/current-market-segmentations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/current-market-segmentations').as('postEntityRequest');
    cy.intercept('DELETE', '/api/current-market-segmentations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (currentMarketSegmentation) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/current-market-segmentations/${currentMarketSegmentation.id}`,
      }).then(() => {
        currentMarketSegmentation = undefined;
      });
    }
  });

  it('CurrentMarketSegmentations menu should load CurrentMarketSegmentations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('current-market-segmentation');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CurrentMarketSegmentation').should('exist');
    cy.url().should('match', currentMarketSegmentationPageUrlPattern);
  });

  describe('CurrentMarketSegmentation page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(currentMarketSegmentationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CurrentMarketSegmentation page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/current-market-segmentation/new$'));
        cy.getEntityCreateUpdateHeading('CurrentMarketSegmentation');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', currentMarketSegmentationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/current-market-segmentations',
          body: currentMarketSegmentationSample,
        }).then(({ body }) => {
          currentMarketSegmentation = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/current-market-segmentations+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/current-market-segmentations?page=0&size=20>; rel="last",<http://localhost/api/current-market-segmentations?page=0&size=20>; rel="first"',
              },
              body: [currentMarketSegmentation],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(currentMarketSegmentationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CurrentMarketSegmentation page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('currentMarketSegmentation');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', currentMarketSegmentationPageUrlPattern);
      });

      it('edit button click should load edit CurrentMarketSegmentation page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CurrentMarketSegmentation');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', currentMarketSegmentationPageUrlPattern);
      });

      it('edit button click should load edit CurrentMarketSegmentation page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CurrentMarketSegmentation');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', currentMarketSegmentationPageUrlPattern);
      });

      it('last delete button click should delete instance of CurrentMarketSegmentation', () => {
        cy.intercept('GET', '/api/current-market-segmentations/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('currentMarketSegmentation').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', currentMarketSegmentationPageUrlPattern);

        currentMarketSegmentation = undefined;
      });
    });
  });

  describe('new CurrentMarketSegmentation page', () => {
    beforeEach(() => {
      cy.visit(`${currentMarketSegmentationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CurrentMarketSegmentation');
    });

    it('should create an instance of CurrentMarketSegmentation', () => {
      cy.get(`[data-cy="value"]`).type('cross-platform Awesome payment').should('have.value', 'cross-platform Awesome payment');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        currentMarketSegmentation = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', currentMarketSegmentationPageUrlPattern);
    });
  });
});

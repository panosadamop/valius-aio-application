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

describe('MarketSegmentationType e2e test', () => {
  const marketSegmentationTypePageUrl = '/market-segmentation-type';
  const marketSegmentationTypePageUrlPattern = new RegExp('/market-segmentation-type(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const marketSegmentationTypeSample = { value: 'Functionality', language: 'ENGLISH' };

  let marketSegmentationType;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/market-segmentation-types+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/market-segmentation-types').as('postEntityRequest');
    cy.intercept('DELETE', '/api/market-segmentation-types/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (marketSegmentationType) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/market-segmentation-types/${marketSegmentationType.id}`,
      }).then(() => {
        marketSegmentationType = undefined;
      });
    }
  });

  it('MarketSegmentationTypes menu should load MarketSegmentationTypes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('market-segmentation-type');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MarketSegmentationType').should('exist');
    cy.url().should('match', marketSegmentationTypePageUrlPattern);
  });

  describe('MarketSegmentationType page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(marketSegmentationTypePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MarketSegmentationType page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/market-segmentation-type/new$'));
        cy.getEntityCreateUpdateHeading('MarketSegmentationType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/market-segmentation-types',
          body: marketSegmentationTypeSample,
        }).then(({ body }) => {
          marketSegmentationType = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/market-segmentation-types+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/market-segmentation-types?page=0&size=20>; rel="last",<http://localhost/api/market-segmentation-types?page=0&size=20>; rel="first"',
              },
              body: [marketSegmentationType],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(marketSegmentationTypePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MarketSegmentationType page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('marketSegmentationType');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypePageUrlPattern);
      });

      it('edit button click should load edit MarketSegmentationType page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketSegmentationType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypePageUrlPattern);
      });

      it('edit button click should load edit MarketSegmentationType page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketSegmentationType');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypePageUrlPattern);
      });

      it('last delete button click should delete instance of MarketSegmentationType', () => {
        cy.intercept('GET', '/api/market-segmentation-types/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('marketSegmentationType').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypePageUrlPattern);

        marketSegmentationType = undefined;
      });
    });
  });

  describe('new MarketSegmentationType page', () => {
    beforeEach(() => {
      cy.visit(`${marketSegmentationTypePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MarketSegmentationType');
    });

    it('should create an instance of MarketSegmentationType', () => {
      cy.get(`[data-cy="value"]`).type('extend USB').should('have.value', 'extend USB');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        marketSegmentationType = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', marketSegmentationTypePageUrlPattern);
    });
  });
});

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

describe('MarketSegmentationTypeB2b e2e test', () => {
  const marketSegmentationTypeB2bPageUrl = '/market-segmentation-type-b-2-b';
  const marketSegmentationTypeB2bPageUrlPattern = new RegExp('/market-segmentation-type-b-2-b(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const marketSegmentationTypeB2bSample = { value: 'Technician', language: 'ENGLISH' };

  let marketSegmentationTypeB2b;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/market-segmentation-type-b-2-bs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/market-segmentation-type-b-2-bs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/market-segmentation-type-b-2-bs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (marketSegmentationTypeB2b) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/market-segmentation-type-b-2-bs/${marketSegmentationTypeB2b.id}`,
      }).then(() => {
        marketSegmentationTypeB2b = undefined;
      });
    }
  });

  it('MarketSegmentationTypeB2bs menu should load MarketSegmentationTypeB2bs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('market-segmentation-type-b-2-b');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MarketSegmentationTypeB2b').should('exist');
    cy.url().should('match', marketSegmentationTypeB2bPageUrlPattern);
  });

  describe('MarketSegmentationTypeB2b page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(marketSegmentationTypeB2bPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MarketSegmentationTypeB2b page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/market-segmentation-type-b-2-b/new$'));
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2b');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2bPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/market-segmentation-type-b-2-bs',
          body: marketSegmentationTypeB2bSample,
        }).then(({ body }) => {
          marketSegmentationTypeB2b = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/market-segmentation-type-b-2-bs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/market-segmentation-type-b-2-bs?page=0&size=20>; rel="last",<http://localhost/api/market-segmentation-type-b-2-bs?page=0&size=20>; rel="first"',
              },
              body: [marketSegmentationTypeB2b],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(marketSegmentationTypeB2bPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MarketSegmentationTypeB2b page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('marketSegmentationTypeB2b');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2bPageUrlPattern);
      });

      it('edit button click should load edit MarketSegmentationTypeB2b page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2b');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2bPageUrlPattern);
      });

      it('edit button click should load edit MarketSegmentationTypeB2b page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2b');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2bPageUrlPattern);
      });

      it('last delete button click should delete instance of MarketSegmentationTypeB2b', () => {
        cy.intercept('GET', '/api/market-segmentation-type-b-2-bs/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('marketSegmentationTypeB2b').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2bPageUrlPattern);

        marketSegmentationTypeB2b = undefined;
      });
    });
  });

  describe('new MarketSegmentationTypeB2b page', () => {
    beforeEach(() => {
      cy.visit(`${marketSegmentationTypeB2bPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2b');
    });

    it('should create an instance of MarketSegmentationTypeB2b', () => {
      cy.get(`[data-cy="value"]`).type('deposit the Kansas').should('have.value', 'deposit the Kansas');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        marketSegmentationTypeB2b = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', marketSegmentationTypeB2bPageUrlPattern);
    });
  });
});
